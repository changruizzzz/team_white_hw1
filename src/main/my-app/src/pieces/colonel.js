import Piece from "./piece.js";
import colonel_2 from "../img/colonel_2.png";
export default class Colonel extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-colonel.svg" : colonel_2))
        this.rank = 8
    }
}