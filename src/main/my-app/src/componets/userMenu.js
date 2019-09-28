import React, { Component } from "react";
import boardInitialization from "./boardInitialization";

export default class UserMenu extends Component{
    render() {
        return (
            <div>
                <button>New Game</button>
                <button onClick={this.props.setup}>Setup</button>
                <button onClick={this.props.onSurrender}>Surrender</button>
                <button>Display Past Game</button>
                <button>Quick Auto Play</button>
            </div>
        )
    }
}