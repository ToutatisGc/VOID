import {CHANGE_CLIENT_HEIGHT} from "./Actions";
import innerState from "./store/State"
import { combineReducers } from 'redux'

function clientHeight(state = innerState.clientHeight,action:Record<string, any>) {
    switch (action.type){
        case CHANGE_CLIENT_HEIGHT:
            return action.data
        default:
            return state
    }
}

export default combineReducers({
    clientHeight
})