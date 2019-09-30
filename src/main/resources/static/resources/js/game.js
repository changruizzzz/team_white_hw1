function loadAll(data) {
    let b = document.getElementById("board");
    for(let i = 0; i < 10; i++) {
        let tr = b.rows[i];
        for(let j = 0; j < 10; j++) {
            let r = data['pieces'][i][j]['rank'];
            let c = classes[Math.abs(r)];
            let btn = tr.cells[j].firstChild;
            let visible = data['pieces'][i][j]['visible'];
            if(r > 0) {
                btn.className += ' blue';
                btn.className += " " + c;
            }
            if(r < 0) {
                btn.className += ' red';
                if(visible) {
                    btn.className += " " + c;
                }
            }
        }
    }
}

function creatPiece(piece, otherClass) {
    let r = piece['rank'];
    let visible = piece['visible'];
    let base = "piece btn " + otherClass;
    let c = classes[Math.abs(r)];
    if(r > 0) {
        base += ' blue ' + c;
    }
    if(r < 0) {
        base += ' red ';
        if(visible) {
            base += c;
        }
    }
    return $("<button>", {"class": base});
}

function fetchAndLoadAll(id, code) {
    board.empty();
    for(let i = 0; i < 10; i++) {
        let tr = $("<tr></tr>").appendTo(board);
        for(let j = 0; j < 10; j++) {
            $("<td><button class='piece btn'></button></td>").appendTo(tr);
        }
    }
    $.ajax({
        type:"POST",
        url:'/api/games/' + id + "/" + code,
        data:{
        },
        success : function(data){
            loadAll(data);
        }
    });
    lakes.forEach(addLake);
}

function userMove(selected) {
    let col0 = $(selected[0]).closest('td').index();
    let row0 = $(selected[0]).closest('tr').index();

    let col1 = $(selected[1]).closest('td').index();
    let row1 = $(selected[1]).closest('tr').index();

    $.ajax({
        type:"POST",
        url:'/game/' + gameId + "/processMove",
        data:{
            x1: row0,
            y1: col0,
            x2: row1,
            y2: col1
        },
        success : function(response){
            let res = processResponse(response);
            if(res && !response['gameEnd'])
                askMove('r');
        }
    });
}

function askMove(side) {
    $.ajax({
        type:"POST",
        url:'/game/' + gameId + "/askMove",
        data:{
            side: side
        },
        success : function(response){
            processResponse(response);
        }
    });
}

function processResponse(response) {
    console.log(response);
    let success = response["success"];
    let message = response["message"];
    let tarPiece = response['piece'];

    if(success) {
        if(message === 'draw') {
            //alert("draw");
        } else if(message === 'loss') {
            //alert("loss");
        } else {
            //alert("win or empty");
        }
        let x1 = response['x'];
        let y1 = response['y'];
        let x2 = tarPiece['x'];
        let y2 = tarPiece['y'];
        renderFromResponse(tarPiece, x1, y1, x2, y2);
        if(response['gameEnd']) {
            //alert("Game end");
            disableBtn($('.pieces'));
            disableBtn(quickPlayBtn);
            enableBtn(replayBtn);
        }
        return true;
    } else {
        //alert(response["message"]);
    }
    return false;
}
function findBtn(x, y) {
    let b = document.getElementById("board");
    return $(b.rows[x].cells[y].children[0]);
}


function renderFromResponse(tarPiece, x1, y1, x2, y2) {
    let btn1 = findBtn(x1, y1);
    let btn2 = findBtn(x2, y2);
    btn2.replaceWith(creatPiece(tarPiece, "game-stage"));
    btn1.replaceWith("<button class='piece btn game-stage'></button>");
}
function swap(selected) {

    let col0 = $(selected[0]).closest('td').index();
    let row0 = $(selected[0]).closest('tr').index();

    let col1 = $(selected[1]).closest('td').index();
    let row1 = $(selected[1]).closest('tr').index();

    //front end swap
    let temp0 = selected[0].clone();
    let temp1 = selected[1].clone();
    selected[0].replaceWith(temp1);
    selected[1].replaceWith(temp0);

    //back end swap
    $.ajax({
        type:"POST",
        url:'/game/' + gameId + "/swap",
        data:{
            x1: row0,
            y1: col0,
            x2: row1,
            y2: col1
        },
        success : function(data){

        }
    });
}

function gameStart() {
    $.ajax({
        type:"POST",
        url:'/game/' + gameId + "/start",
        success : function(data){
            $(".setup-stage").removeClass("setup-stage");
            $("#startBtn").attr("disabled",true);
            $(".piece").addClass("game-stage");
        }
    });
}

function addLake(cor) {
    let btn = findBtn(cor[0], cor[1]);
    btn.addClass("lake");
    btn.attr("disabled", true);
}

function disableBtn(btn) {
    btn.attr("disabled", true);
}

function enableBtn(btn) {
    btn.attr("disabled", false);
}

let board = $("#board");
let classes = ['', 'spy', 'scout', 'miner', 'sergeant', 'lieutenant',
                'captain', 'major', 'colonel', 'general', 'marshal',
                'flag', '', 'bomb'];

let startGameBtn = $("#startBtn");
let quickPlayBtn = $("#quickPlay");
let replayBtn = $("#replayBtn");
let replayMoveBtn = $('#replayMoveBtn');
let selected = [];

let lakes = [[4,2],[4,3],[5,2],[5,3],[4,6],[4,7],[5,6],[5,7]];

fetchAndLoadAll(gameId, "current");



if(ended) {
    disableBtn($('.pieces'));
    enableBtn(replayBtn);
} else {
    let pieces = $(".piece");
    if (started) {
        pieces.addClass("game-stage");
        enableBtn(quickPlayBtn);
    } else {
        enableBtn(startGameBtn);
        pieces.addClass("setup-stage");
    }
}

$(document).on('click', '.setup-stage', function () {
    let row = $(this).closest('tr').index();
    if(row < 6) {
        selected = [];
        return;
    }
    selected.push($(this));
    if(selected.length === 2) {
        swap(selected);
        selected = []
    }
});

$(document).on('click', '.game-stage', function () {
    if(selected.length === 0 && !$(this).hasClass("blue")) {
        return;
    }
    if(selected.length === 1 && ($(this).hasClass("blue") || $(this) === selected[0])) {
        selected = [];
    }
    selected.push($(this));
    if(selected.length === 2) {
        userMove(selected);
        selected = []
    }

});


startGameBtn.click(function () {
    gameStart();
    enableBtn(quickPlayBtn);
    disableBtn(startGameBtn);
});

quickPlayBtn.click(function () {
    disableBtn(quickPlayBtn);
    askMove('b');
    askMove('r');
    enableBtn(quickPlayBtn)
});
let move = [];
replayBtn.click(function () {
    fetchAndLoadAll(gameId,"initial");
    enableBtn(replayMoveBtn);
    $.ajax({
        type:"POST",
        url:'/api/games/' + gameId + "/moves",
        data:{
        },
        success : function(data){
            move = data;
        }
    });
});

replayMoveBtn.click(function () {
    let response = move.shift();
    let tarPiece = response['piece'];
    let x1 = response['x'];
    let y1 = response['y'];
    let x2 = tarPiece['x'];
    let y2 = tarPiece['y'];
    renderFromResponse(tarPiece, x1, y1, x2, y2);
    if(response['gameEnd']) {
        disableBtn($('.pieces'));
        disableBtn(replayMoveBtn);
    }
});