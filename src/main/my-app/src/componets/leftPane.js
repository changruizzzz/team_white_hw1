import React, { Component } from "react";

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
        <div>
          {pieces[i]} -- {this.props.player1Pieces[i]} -{" "}
          {this.props.player2Pieces[i]}
        </div>
      );
      pane.push(row);
    }

    return <div>{pane}</div>;
  }
}
