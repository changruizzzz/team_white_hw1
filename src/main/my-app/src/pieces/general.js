import Piece from "./piece.js";
import general_2 from "../img/general_2.png";
export default class Genreal extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-general.svg" : general_2))
        this.rank = 9
    }
}