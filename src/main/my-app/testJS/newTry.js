let board = $("#board");
let classes = ['', 'spy', 'scout', 'miners', 'sergeant', 'lieutenant',
    'captain', 'major', 'colonel', 'general', 'marshall',
    'flag', '', 'bomb'];
let preRow = null;
let preCol = null;
let player = null;
for(let i = 0; i < 10; i++) {
    let tr = $("<tr></tr>").appendTo(board);
    for(let j = 0; j < 10; j++) {
        $("<td><button class='piece btn'><div class='cover'></div></button></td>").appendTo(tr);
    }
}

function loadAll(data) {
    let b = document.getElementById("board");
    for(let i = 0; i < 10; i++) {
        let tr = b.rows[i];
        for(let j = 0; j < 10; j++) {
            let r = data['board'][i][j]['rank'];
            let c = classes[Math.abs(r)];
            let btn = tr.cells[j].firstChild;
            let visible = data['board'][i][j]['visible'];
            if(r > 0) {
                btn.firstChild.className += ' blue';
                tr.cells[j].firstChild.className += " " + c;
            }
            if(r < 0) {
                btn.firstChild.className += ' red';
                if(visible) {
                    tr.cells[j].firstChild.className += " " + c;
                }
            }


        }
    }
}

function fetchAndLoadAll(set_id, btn) {
    $.ajax({
        type:"POST",
        url:'/api/games/7',
        data:{
        },
        success : function(data){
            console.log(data);
            loadAll(data);
        }
    });
}

$("#load").click(function () {
    fetchAndLoadAll()
});

$(".piece").click(function () {
    let col = $(this).closest('td').index();
    let row = $(this).closest('tr').index();
    let player = board[i][j]['player'];

    if (player !== 1 || player !== 2) {
        return
    }
    //No piece has been selected yet
    if (!preCol || !preRow) {
        //Select empty square or wrong team piece
        if (!board[row][col] || board[row][col][player] !== player) {
            return
            //Select correct team piece
        } else {
            //Highlight the piece at index of dest
            board[row][col].style = { ...board[row][col].style,
                backgroundColor: "RGB(111,143,114)"
            };
            return
        }
        //There is one piece which has been selected
    } else {
        //Remove source piece background shade
        board[preRow][preCol].style = { ...board[preRow][preCol].style,
            backgroundColor: ""
        };
        // Pass indexes (preRow, preCol), (row, col) to back-end
    }

});