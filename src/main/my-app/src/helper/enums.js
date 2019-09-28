export const enums = {
    Player: {
        PLAYER1: 1,
        PLAYER2: 2,
    },
    Piece: {
        FALLEN_PIECE_HEIGHT: "50px",
        FALLEN_PIECE_WIDTH: "50px",
    },
    PieceRank: {
        FLAG: 0,
        SPY: 1,
        SCOUT: 2,
        MINER: 3,
        SERGEANT: 4,
        LIEUTENANT: 5,
        CAPTAIN: 6,
        MAJOR: 7,
        COLONEL: 8,
        GENERAL: 9,
        MARSHALL: 10,
        BOMB:11,
    },
    Winner: {
        PLAYER1: 1,
        PLAYER2: 2,
        NO_PLAYER_WINS: null,
        TIE: 3,
    },
    customMode: {
        OPEN: true,
        CLOSE: false,
    },
    PlayerName: {
        PLAYER1_NAME: "Red Team",
        PLAYER2_NAME: "Blue_Team",
    },
    Message: {
        PLAYER1_WINS: "Red Team wins",
        PLAYER2_WINS: "Blue Team wins",
        Tie: "tie",
        WRONG_PLAYER_SELECTION: "Wrong pieces selection",
        WRONG_SOURCE_OR_DESTINATION_SELECTION: "Wrong selection. Choose valid source and destination again.",
        CHOOSE_DESTINATION: "Choose destination for the selected piece",
        Empty: "",
    },
    BackgroundColor: {
        BACKGROUND_SHADE: "RGB(111,143,114)",

        NO_COLOR: "",
    },
    SelectObj: {
        NO_OBJECT_SELECTED: -1,
    },
}