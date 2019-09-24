import Piece from "./piece.js";
import miner_2 from "../img/miner_2.png";
export default class Miner extends Piece {

  constructor(player) {
    super(
      player,
      player === 1 ? "http://vector.gissen.nl/stratego-miner.svg" : miner_2
    );
      this.rank = 3
  }
}
