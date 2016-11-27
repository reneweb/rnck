
import { NativeModules } from 'react-native';

const { Rnck } = NativeModules;
const KEYBOARD_REF = 'keyboard';

export default class Keyboard extends Component {
  constructor() {
    super();
    this.state = {}
  }

  render() {
    return <RnckKeyboard ref={KEYBOARD_REF} />;
  }
};

const RnckKeyboard = requireNativeComponent('RnckKeyboard', Camera);
