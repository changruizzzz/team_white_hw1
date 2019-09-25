import React, { Component } from "react";
import Square from "./square";
import "../stratego.css";
export default class Board extends Component {
  renderSquare(index, squareShade) {
    return (
      <Square
        key={index}
        piece={this.props.squares[index]}
        style={
          this.props.squares[index] ? this.props.squares[index].style : null
        }
        shade={squareShade}
        onClick={() => this.props.onClick(index)}
      />
    );
  }
  render() {
    const board = [];
    //Push blue team squares
    for (let i = 0; i < 4; i++) {
      const squareRow = [];
      for (let j = 0; j < 10; j++) {
        squareRow.push(this.renderSquare(i * 10 + j, "player2-square"));
      }
      board.push(
        <div key={i} className="board-row">
          {squareRow}
        </div>
      );
    }

    //Push the squares in the middle
    for (let i = 4; i < 6; i++) {
      const squareRow = [];
      for (let j = 0; j < 10; j++) {
        if (j === 2 || j === 3 || j === 6 || j === 7) {
          squareRow.push(this.renderSquare(i * 10 + j, "lake-square"));
        } else {
          squareRow.push(this.renderSquare(i * 10 + j, "mid-square"));
        }
      }
      board.push(
        <div key={i} className="board-row">
          {squareRow}
        </div>
      );
    }

    //Push red team squares
    for (let i = 6; i < 10; i++) {
      const squareRow = [];
      for (let j = 0; j < 10; j++) {
        squareRow.push(this.renderSquare(i * 10 + j, "player1-square"));
      }
      board.push(
        <div key={i} className="board-row">
          {squareRow}
        </div>
      );
    }
    return <div>{board}</div>;
  }
}
