import { NativeModules, requireNativeComponent, View } from 'react-native';
import React, { Component, PropTypes } from 'react';

const { Rnck } = NativeModules;
const KEYBOARD_REF = 'keyboard';

export default class Keyboard extends Component {

  static propTypes = {
    ...View.propTypes,
    keys: PropTypes.array
  }

  constructor() {
    super();
    this.state = {}
  }

  render() {
    return <RnckKeyboard {...this.props} />;
  }
};

const RnckKeyboard = requireNativeComponent('RnckKeyboard', Keyboard);
