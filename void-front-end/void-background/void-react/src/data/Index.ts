import { applyMiddleware, createStore } from 'redux'
import thunk from 'redux-thunk'
import reducers from './Reducers'

// 创建store
let store = createStore(reducers, applyMiddleware(thunk))
export default store