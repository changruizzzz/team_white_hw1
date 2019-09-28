import React, { Component } from "react";
import UserMenu from "./userMenu";
export default class LeftPane extends Component {
  render() {
    const pane = [];
    const pieces = [
      "Flag",
      "Spy",
      "Scouts",
      "Miners",
      "Sergeants",
      "Lieutenants",
      "Captains",
      "Majors",
      "Colonels",
      "General",
      "Marshal",
      "Bomb"
    ];
    for (let i = 0; i < 11; i++) {
      const row = (
        <div key={i}>
          {pieces[i]} -- {this.props.player1Pieces[i]} -{" "}
          {this.props.player2Pieces[i]}
        </div>
      );
      pane.push(row);
    }

    return (
      <div>
        {pane}
        <UserMenu setup={this.props.setup}
                  onSurrender={this.props.onSurrender}
                  onNewGame={this.props.onNewGame}
                  onPlay={this.props.onPlay}
                  winner={this.props.winner}
                  player={this.props.player}
                  modifyingMode={this.props.modifyingMode}
        />
      </div>
    );
  }
}
