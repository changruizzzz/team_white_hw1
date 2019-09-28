import Piece from "./piece.js";
import scout_2 from "../img/scout_2.png";
export default class Scout extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-scout.svg" : scout_2))
        this.rank = 2
    }
}