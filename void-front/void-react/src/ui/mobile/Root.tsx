import React from 'react';
import logo from '../../logo.svg';
import '../../css/App.css';
import { Button } from 'antd-mobile';
import config from '../../Config'
import Clock from "../desktop/Clock";

class Root extends React.Component<any, any> {

  render() {
    return (
        <div className="App" style={{height:'100%'}}>
          <header className="App-header">
            <img src={logo} className="App-logo" alt="logo" />
            <Button color="primary">Button</Button>
            {/*<Button></Button>*/}
            <p>
              Edit <code>src/Root.tsx</code> and save to reload.
            </p>
            <Clock/>
            <div>{this.props.height}</div>
            <a hidden={config.nav.alwaysHiddenNavBar}
                className="App-link"
                href="https://reactjs.org"
                target="_blank"
                rel="noopener noreferrer"
            >
              Learn React
            </a>
          </header>
        </div>
    );
  }
}

export default Root;
