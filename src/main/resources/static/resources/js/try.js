
'use strict';

const e = React.createElement;

class LikeButton extends React.Component {
    state = {
        isLoading: true,
        groups: []
    };
    constructor(props) {
        super(props);

    }
    async componentDidMount() {
        const response = await fetch('/api/users');
        const body = await response.json();
        this.setState({ groups: body, isLoading: false});
    }

    render() {
        if (this.state.isLoading) {
            return 'Loading';
        }
        return this.state.groups[0].username;
    }
}
const domContainer = document.querySelector('#like_button_container');
ReactDOM.render(e(LikeButton), domContainer);