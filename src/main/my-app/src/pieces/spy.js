import Piece from "./piece.js";
import spy_2 from "../img/spy_2.png";
export default class Spy extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-spy.svg" : spy_2))
        this.rank = 1
    }
}