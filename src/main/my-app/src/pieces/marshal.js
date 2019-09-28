import Piece from "./piece.js";
import marshal_2 from "../img/marshal_2.png";
export default class Marshal extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-marshal.svg" : marshal_2))
        this.rank = 10
    }
}