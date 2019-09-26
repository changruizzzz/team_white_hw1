import React, {Component} from 'react';
import Square from "./square";
import '../stratego.css';
export default class FallenPieces extends Component {
    renderSquare(square, index) {
        return(
            <Square key={index} piece={square} index={index} style={square.style}/>
        )
    }
    render() {
        return (
          <div>
              <div className="board-row">{this.props.player2FallenPieces.map((p2p, index) => this.renderSquare(p2p, index))}</div>
              <div className="board-row">{this.props.player1FallenPieces.map((p1p,index) => this.renderSquare(p1p, index))}</div>
          </div>
        );
    }
}
