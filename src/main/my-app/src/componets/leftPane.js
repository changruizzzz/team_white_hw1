import React, { Component } from 'react'

export default class LeftPane extends Component{
    initialLeftPane() {

    }
    render() {
        return (
            <div className="leftPane">
                <div>10: Marshal -- <span className="red">1</span> - <span className="blue">1</span></div>
                <div>9: General -- <span className="red">1</span> - <span className="blue">1</span></div>
                <div>8: Colonels -- <span className="red">2</span> - <span className="blue">2</span></div>
                <div>7: Majors -- <span className="red">3</span> - <span className="blue">3</span></div>
                <div>6: Captains -- <span className="red">4</span> - <span className="blue">4</span></div>
                <div>5: Lieutenants -- <span className="red">4</span> - <span className="blue">4</span></div>
                <div>4: Sergeants -- <span className="red">4</span> - <span className="blue">4</span></div>
                <div>3: Miners -- <span className="red">5</span> - <span className="blue">5</span></div>
                <div>2: Scouts -- <span className="red">8</span> - <span className="blue">8</span></div>
                <div>1: Spy -- <span className="red">1</span> - <span className="blue">1</span></div>
            </div>
        )
    }
}