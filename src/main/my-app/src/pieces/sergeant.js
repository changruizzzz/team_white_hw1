import Piece from "./piece.js";
import sergeant_2 from "../img/sergeant_2.png";
export default class Sergeant extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-sergeant.svg" : sergeant_2))
        this.rank = 4
    }
}