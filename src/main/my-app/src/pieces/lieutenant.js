import Piece from "./piece.js";
import lieutenant_2 from "../img/lieutenant_2.png";
export default class Lieutenant extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-lieutenant.svg" : lieutenant_2))
        this.rank = 5
    }
}