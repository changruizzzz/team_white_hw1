import React, { Component } from "react";

export default class UserMenu extends Component{
    render() {
        return (
            <div>
                <button disabled={this.props.player || this.props.modifyingMode} onClick={this.props.onNewGame}>New Game</button>
                <button disabled={!this.props.modifyingMode} onClick={this.props.onPlay}>Play</button>
                <button disabled={!this.props.modifyingMode} onClick={this.props.setup}>Setup</button>
                <button disabled={!this.props.player} onClick={this.props.onSurrender}>Surrender</button>
                <button disabled={this.props.modifyingMode || this.props.player}>Display Past Game</button>
                <button disabled={!this.props.player} >Quick Auto Play</button>
            </div>
        )
    }
}