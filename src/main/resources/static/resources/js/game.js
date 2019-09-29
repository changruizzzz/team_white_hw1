let board = $("#board");
let classes = ['', 'spy', 'scout', 'miner', 'sergeant', 'lieutenant',
                'captain', 'major', 'colonel', 'general', 'marshal',
                'flag', '', 'bomb'];

let selected = [];

for(let i = 0; i < 10; i++) {
    let tr = $("<tr></tr>").appendTo(board);
    for(let j = 0; j < 10; j++) {
        $("<td><button class='piece btn'></button></td>").appendTo(tr);
    }
}

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

function fetchAndLoadAll(id) {
    $.ajax({
        type:"POST",
        url:'/api/games/' + id,
        data:{
        },
        success : function(data){
            loadAll(data);
        }
    });
}

fetchAndLoadAll(gameId);

let pieces = $(".piece");

if(!started) {
    pieces.addClass("setup-stage");
} else {
    pieces.addClass("game-stage");
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
        // alert("move");
        userMove(selected);
        selected = []
    }

});
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
        success : function(data){
            console.log(data);
            let success = data["success"];
            let message = data["message"];
            if(success) {
                if(message === 'draw') {
                    // alert("draw");
                } else if(message === 'loss') {
                    // alert("loss");
                } else {
                    // alert("win or empty");
                }
                selected[1].replaceWith(creatPiece(data['piece'], "game-stage"));
                selected[0].replaceWith("<button class='piece btn game-stage'></button>");
            } else {
                alert(data["message"]);
            }
            if(data['gameEnd']) {
                alert("Game end");
            }
        }
    });
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

$("#startBtn").click(function () {
    gameStart();
});

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
