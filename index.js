import { NativeModules, requireNativeComponent, View, DeviceEventEmitter, Platform } from 'react-native';
import React, { Component, PropTypes } from 'react';

const { Rnck } = NativeModules;

export default class Keyboard extends Component {

  static propTypes = {
    ...View.propTypes,
    keys: PropTypes.array,
    show: PropTypes.bool,
    onKeyPress: PropTypes.func
  }

  constructor() {
    super();
    this.state = {}
  }

  componentWillMount() {
    DeviceEventEmitter.addListener('onKeyPress', (e: Event) => {
      this.props.onKeyPress(e)
    });
  }

  render() {
    const {onKeyPress, ...props} = this.props;

    props.onKeyPress = onKeyPress && ((event: Event) => {
          if (Platform.OS === 'ios') {
            onKeyPress && onKeyPress(event.nativeEvent);
          } else {
            onKeyPress && onKeyPress(event);
          }
        });

    return <RnckKeyboard {...props} />;
  }
};

const RnckKeyboard = requireNativeComponent('RnckKeyboard', Keyboard);
