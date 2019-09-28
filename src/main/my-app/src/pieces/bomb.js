import Piece from "./piece.js";
import bomb_2 from "../img/bomb_2.png";
export default class Bomb extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-bomb.svg" :bomb_2))
        this.rank = 11;
    }
}