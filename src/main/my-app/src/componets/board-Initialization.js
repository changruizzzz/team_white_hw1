import Miner from '../pieces/miner';
import Bomb from '../pieces/bomb';
import General from '../pieces/general';
import Lieutenant from "../pieces/lieutenant";
import Major from "../pieces/major";
import Marshal from "../pieces/marshal";
import Scout from "../pieces/scout";
import Sergeant from "../pieces/sergeant";
import Spy from "../pieces/spy";
import Flag from "../pieces/flag";
import Colonel from "../pieces/colonel";
export default function boardInitialization() {
    const squares = Array(100).fill(null);
    squares[85] = new Miner(1);
    squares[93] = new Flag(1);
    squares[92] = new Bomb(1);
    squares[83] = new Bomb(1);
    squares[94] = new Bomb(1);
    squares[91] = new Lieutenant(1);
    squares[90] = new Major(1);
    squares[89] = new Marshal(1);
    squares[88] = new Scout(1);
    squares[87] = new Sergeant(1);
    squares[86] = new Spy(1);
    squares[95] = new Colonel(1);
    squares[82] = new General(1);
    squares[15] = new Miner(2);
    return squares;
}