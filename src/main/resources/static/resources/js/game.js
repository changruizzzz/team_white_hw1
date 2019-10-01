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
                btn.setAttribute("data-toggle", "tooltip");
                btn.className += ' bluePiece';
                btn.className += " " + c;
                btn.setAttribute("title", c);
            }
            if(r < 0) {
                btn.className += ' redPiece';
                if(visible) {
                    btn.className += " " + c;
                    btn.setAttribute("data-toggle", "tooltip");
                    btn.setAttribute("title", c);
                }
            }
            updateCountByRank(r, 1);
        }
    }
}

function updateCountByRank(r, n) {
    let c = classes[Math.abs(r)];
    let selector = '#' + c + "-left";
    if(r > 0) {
        let info = $(selector).find(".alert-primary");
        info.text(parseInt(info.text()) + n);
    }
    if(r < 0) {
        let info = $(selector).find(".alert-danger");
        info.text(parseInt(info.text()) + n);
    }
}
function creatPiece(piece, otherClass) {
    let r = piece['rank'];
    let visible = piece['visible'];
    let base = "piece " + otherClass;
    let c = classes[Math.abs(r)];
    if(r > 0) {
        base += ' bluePiece ' + c;
        return $("<button>", {"class": base, "date-toggle":"tooltip", "title":c});
    }
    if(r < 0) {
        base += ' redPiece ';
        if(visible) {
            base += c;
            return $("<button>", {"class": base, "date-toggle":"tooltip", "title":c});
        }
    }
    return $("<button>", {"class": base});
}

function fetchAndLoadAll(id, code) {
    board.empty();
    $("#response-body").empty();
    $('.piece-info .alert').text(0);
    for(let i = 0; i < 10; i++) {
        let tr = $("<tr></tr>").appendTo(board);
        for(let j = 0; j < 10; j++) {
            $("<td><button class='piece '></button></td>").appendTo(tr);
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
            processResponse(response, false);
            if(side === 'b' && !response['gameEnd']) {
                askMove('r');
            } else {
                if(askUser) {
                    enableBtn(quickPlayBtn);
                    askUser = false;
                }
            }
        }
    });
}

function handleMessage(message, response) {
    let div = $("<div>", {"class": "game-response"});

    if (message === 'empty') {

    } else {
        let r1 = response['rank1'];
        let r2 = response['rank2'];
        $(getClassSpanFromRank(r1)).appendTo(div);
        $("<span> vs </span>").appendTo(div);
        $(getClassSpanFromRank(r2)).appendTo(div);
        if(message === 'win') {
            updateCountByRank(r2, -1);
            $("<span class='alert alert-success'>win</span>").appendTo(div);
        } else if(message === 'draw') {
            updateCountByRank(r1, -1);
            updateCountByRank(r2, -1);
            $("<span class='alert alert-primary'>draw</span>").appendTo(div);
        } else {
            updateCountByRank(r1, -1);
            $("<span class='alert alert-danger'>loss</span>").appendTo(div);
        }
        div.appendTo($("#response-body"));
    }

}

function processResponse(response, isReplay) {
    console.log(response);
    let success = response["success"];
    let message = response["message"];
    let tarPiece = response['piece'];

    if(success) {
        invalidWrapper.css("visibility", "hidden");
        handleMessage(message, response);
        let x1 = response['x'];
        let y1 = response['y'];
        let x2 = tarPiece['x'];
        let y2 = tarPiece['y'];
        renderFromResponse(tarPiece, x1, y1, x2, y2, isReplay);
        if(response['gameEnd']) {
            endGame();
        }
        return true;
    } else {
        if(response['gameEnd']) {
            endGame();
        } else {
            invalidMessageSpan.text(message);
            invalidWrapper.css("visibility", "visible");
        }
    }
    return false;
}
function findBtn(x, y) {
    let b = document.getElementById("board");
    return $(b.rows[x].cells[y].children[0]);
}


function renderFromResponse(tarPiece, x1, y1, x2, y2, isReplay) {
    let btn1 = findBtn(x1, y1);
    let btn2 = findBtn(x2, y2);
    if(isReplay) {
        btn2.replaceWith(creatPiece(tarPiece, ""));
        btn1.replaceWith("<button class='piece'></button>");
    } else {
        btn2.replaceWith(creatPiece(tarPiece, "game-stage"));
        btn1.replaceWith("<button class='piece game-stage'></button>");
    }
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
    disableBtn(startGameBtn);
    $.ajax({
        type:"POST",
        url:'/game/' + gameId + "/start",
        success : function(data){
            $(".setup-stage").removeClass("setup-stage");
            $(".piece").addClass("game-stage");
            enableBtn(quickPlayBtn);
            enableBtn(surrenderBtn);
            enableBtn(playAgainBtn);
            stageInfo.text("game stage");
        }
    });
}

function endGame() {
    $(".piece").removeClass("game-stage");
    disableBtn(quickPlayBtn);
    disableBtn(surrenderBtn);
    enableBtn(replayBtn);
    stageInfo.text("game end");
}

function surrender() {
    $.ajax({
        type:"POST",
        url:'/game/' + gameId + "/surrender",
        success : function(data){
            endGame();
        }
    });
}

function getClassSpanFromRank(r) {
    console.log(r);
    let c = classes[Math.abs(r)];
    let side;
    if(r > 0)
        side = 'blueInfo';
    if(r < 0)
        side = 'redInfo';
    return '<div class="' + side + '">' + c + "</div>";
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

let surrenderBtn = $("#surrenderBtn");
let stageInfo = $("#stage-info");
let startGameBtn = $("#startBtn");
let quickPlayBtn = $("#quickPlay");
let replayBtn = $("#replayBtn");
let replayMoveBtn = $('#replayMoveBtn');
let stopReplayBtn = $("#stopReplayBtn");
let playAgainBtn = $("#playAgainBtn");
let selected = [];
let invalidWrapper = $("#invalidMoveWrapper");
let invalidMessageSpan = $("#invalidMoveInfo");

let lakes = [[4,2],[4,3],[5,2],[5,3],[4,6],[4,7],[5,6],[5,7]];

fetchAndLoadAll(gameId, "current");



if(ended) {
    enableBtn(replayBtn);
    stageInfo.text("game end");
    enableBtn(playAgainBtn);
} else {
    let pieces = $(".piece");
    if (started) {
        pieces.addClass("game-stage");
        stageInfo.text("game stage");
        enableBtn(quickPlayBtn);
        enableBtn(surrenderBtn);
        enableBtn(playAgainBtn);
    } else {
        enableBtn(startGameBtn);
        stageInfo.text("setup stage");
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
    if(selected.length === 0 && !$(this).hasClass("bluePiece")) {
        return;
    }
    if(selected.length === 1 && ($(this).hasClass("bluePiece") || $(this) === selected[0])) {
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
});

let askUser = false;

quickPlayBtn.click(function () {
    askUser = true;
    disableBtn(quickPlayBtn);
    askMove('b');
});

let move = [];

replayBtn.click(function () {
    stageInfo.text("replay");
    fetchAndLoadAll(gameId,"initial");
    $.ajax({
        type:"POST",
        url:'/api/games/' + gameId + "/moves",
        data:{
        },
        success : function(data){
            move = data;
            if(move.length === 0) {
                stageInfo.text("replay end");
                disableBtn(replayMoveBtn);
            }
            else {
                enableBtn(replayMoveBtn);
                enableBtn(stopReplayBtn);
            }
        }
    });
});


replayMoveBtn.click(function () {
    let response = move.shift();
    let tarPiece = response['piece'];
    processResponse(response, true);
    if(move.length === 0) {
        stageInfo.text("replay end");
        disableBtn(replayMoveBtn);
        disableBtn(stopReplayBtn);
    }
});

surrenderBtn.click(function () {
    surrender();
});

stopReplayBtn.click(function () {
    location.reload();
});

playAgainBtn.click(function () {
    $.ajax({
        type:"POST",
        url:'/game/' + gameId + "/playAgain",
        success : function(data){
            console.log(data);
            window.location.href = "/game/" + data;
        }
    });
});