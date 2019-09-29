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

function fetchAndLoadAll(id) {
    $.ajax({
        type:"POST",
        url:'/api/games/' + id,
        data:{
        },
        success : function(data){
            console.log(data);
            loadAll(data);
        }
    });
}
alert(gameId);
fetchAndLoadAll(gameId);

let pieces = $(".piece");
$("#load").click(function () {
    fetchAndLoadAll(gameId)
});

pieces.click(function () {
   let col = $(this).closest('td').index();
   let row = $(this).closest('tr').index();
   console.log(col + ',' + row);
});

if(!started) {
    pieces.addClass("setup-stage");
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