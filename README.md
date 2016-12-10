# rnck (react-native-custom-keyboard)

rnck enables to create custom keyboards in react native.

_Note: this is still very much under development and currently works only on Android_

## Getting started

`$ npm install rnck --save`

`$ react-native link rnck`

## Usage
```javascript
import React, { Component } from 'react';
import {TextInput} from 'react-native';
import Keyboard from 'rnck';

export default class RnckExample extends Component {

  constructor(props) {
    super(props);
    this.state = { showKeyboard: false, text: '' };
  }

  render() {
    return (
      <View>
        <TextInput
          onFocus={() => this.setState({showKeyboard: true})}
          onEndEditing={() => this.setState({showKeyboard: false})}
          value={this.state.text}></TextInput>
        <Keyboard
          keys={[['1','2', '3'], ['4', '5', '6'], ['7', '8', '9']]}
          show={this.state.showKeyboard}
          onKeyPress={(event) => this.setState({text: this.state.text + event.key})}/>
      </View>
    );
  }
}
```
