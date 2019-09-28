import Piece from "./piece.js";
import captain_2 from "../img/captain_2.png";
export default class Captain extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-captain.svg" : captain_2))
        this.rank = 6
    }
}