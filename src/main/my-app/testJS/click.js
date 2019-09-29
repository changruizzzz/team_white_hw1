/*
class piece {
    this.rank = ...
    this.player = ...
}


 */


/*
 * Highlights the piece at the given index. If there is a highlighted
 * piece on the chess board. Check if that piece is able to move to the
 * given index. Returns true if a piece is highlighted, or that highlighted piece moves successfully.
 * Return false if that piece is unable to move.
 *
 * @param { number } dest - Tells the index of clicked piece.
 * @param { number } player - Tells which player's turn.
 *                          - player 1 turn, player = 1
 *                          - player 2 turn, player = 2
 *                           - game does not start, player = null
 * @param { array } squares - Is an array with length of 100. Piece objects are stored in here
 *
 * @return { boolean } - returns true if piece moves successfully, otherwise returns false.
 */
var src; //The index of highlighted piece. src= -1, no highlighted piece
function handleClick(dest, player, squares){
    if (player !== 1 && player !== 2) {
        return;
    }
    //No piece has been selected yet
    if (src < 0) {
        //Select empty square or wrong team piece
        if (!squares[dest] || squares[dest].player !== player) {
            return false
        //Select correct team piece
        } else {
            //Highlight the piece at index of dest
            squares[dest].style = { ...squares[dest].style,
                backgroundColor: "RGB(111,143,114)"
            };
            return true
        } //If one piece has been selected, check the destination

    } else {
        //Remove source piece background shade
        squares[src].style = { ...squares[src].style,
            backgroundColor: ""
        };
        //If the piece in the destination is in the same team, unselect the source piece
        if (squares[dest] && squares[dest].player === this.state.player) {
            this.setState({
                info: "Wrong selection. Choose valid source and destination again.",
                selectedObj: -1
            }); //There is no piece in the destination,
            //or there is a piece which is from different team from the source piece
        } else {
            if (this.state.customMode) {
                const isCustomMovable = this.isCustomeMovable(src, dest);
            }

            const player1FallenPieces = this.state.player1FallenPieces.slice();
            const player2FallenPieces = this.state.player2FallenPieces.slice();
            const isMovable = this.isMovable(src, dest);

            if (isMovable) {
                //Two pieces fight
                if (squares[dest] !== null) {
                    const fight = this.fight(src, dest); //fight: -1 - the source piece lose
                    //       0 - tie
                    //       1 - the source piece win

                    if (squares[dest].player === 1) {
                        if (fight === -1) {
                            squares[src].style = { ...squares[src].style,
                                height: '50px',
                                width: '50px'
                            };
                            player2FallenPieces.push(squares[src]); //Push the defeated piece to fallen pieces array

                            this.pieceAmountDecrement(2, squares[src].rank); //The amount of the defeated piece decreases by one

                            squares[src] = null; //Remove the defeated piece from the board
                        } else if (fight === 0) {
                            squares[src].style = { ...squares[src].style,
                                height: '50px',
                                width: '50px'
                            };
                            squares[dest].style = { ...squares[dest].style,
                                height: '50px',
                                width: '50px'
                            };
                            player1FallenPieces.push(squares[dest]);
                            player2FallenPieces.push(squares[src]);
                            this.pieceAmountDecrement(1, squares[dest].rank);
                            this.pieceAmountDecrement(2, squares[src].rank);
                            squares[src] = null;
                            squares[dest] = null;
                        } else {
                            player1FallenPieces.push(squares[dest]);
                            this.pieceAmountDecrement(1, squares[dest].rank);
                            squares[dest] = squares[src];
                            squares[src] = null;
                        }
                    } else {
                        if (fight === -1) {
                            squares[src].style = { ...squares[src].style,
                                height: '50px',
                                width: '50px'
                            };
                            player1FallenPieces.push(squares[src]);
                            this.pieceAmountDecrement(1, squares[src].rank);
                            squares[src] = null;
                        } else if (fight === 0) {
                            squares[src].style = { ...squares[src].style,
                                height: '50px',
                                width: '50px'
                            };
                            squares[dest].style = { ...squares[dest].style,
                                height: '50px',
                                width: '50px'
                            };
                            player1FallenPieces.push(squares[src]);
                            player2FallenPieces.push(squares[dest]);
                            this.pieceAmountDecrement(1, squares[src].rank);
                            this.pieceAmountDecrement(2, squares[dest].rank);
                            squares[src] = null;
                            squares[dest] = null;
                        } else {
                            squares[dest].style = { ...squares[dest].style,
                                height: '50px',
                                width: '50px'
                            };
                            player2FallenPieces.push(squares[dest]);
                            this.pieceAmountDecrement(2, squares[dest].rank);
                            squares[dest] = squares[src];
                            squares[src] = null;
                        }
                    }
                } else {
                    squares[dest] = squares[src];
                    squares[src] = null;
                }

                let player = this.state.player === 1 ? 2 : 1;
                this.setState({
                    selectedObj: -1,
                    squares: squares,
                    player1FallenPieces: player1FallenPieces,
                    player2FallenPieces: player2FallenPieces,
                    player: player,
                    info: ""
                });
            } else {
                this.setState({
                    info: "Wrong selection. Choose valid source and destination again.",
                    selectedObj: -1
                });
            }
        }
    }
};