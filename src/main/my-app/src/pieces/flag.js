import Piece from "./piece.js";
import flag_2 from "../img/flag_2.png";
export default class Flag extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-flag.svg" : flag_2))
        this.rank = 0
    }
}