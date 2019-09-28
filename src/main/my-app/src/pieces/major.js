import Piece from "./piece.js";
import major_2 from "../img/major_2.png";
export default class Major extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-major.svg" : major_2))
        this.rank = 7
    }
}