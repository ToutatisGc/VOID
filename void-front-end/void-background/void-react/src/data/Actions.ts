export const CHANGE_CLIENT_HEIGHT = "CHANGE_CLIENT_HEIGHT"

export enum APP_TYPE {MALL, USER}

export function setClientHeight() {
    return (dispatch:any, getState:any) => {
        dispatch({ type: CHANGE_CLIENT_HEIGHT, data: 1000 })
    }
}