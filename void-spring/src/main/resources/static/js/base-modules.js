const { reactive } = Vue
const config = reactive({
    autoInsertSpace: false
})

let $http = {
    instance: axios.create(this.config),
    config:{
        // baseURL: '',
        // headers: {},
        timeout: 5000,
        withCredentials:true
    },
    _default:{
        exceptionHandler: function(err){

        }
    },
    create:function(config){
        this.config = config;
        this.instance = axios.create(this.config);
    },
    get:function (url,param){
        return this.request(url,param,'GET');
    },
    post:function (url,param){
        return this.request(url,param,'POST')
    },
    request: function (url, param, method) {
        if (method === 'GET') {
            let idx = 0;
            let paramPath = url;
            for (let field in param) {
                paramPath += idx === 0 ? '?' : '&';
                paramPath += (field + '=' + param[field])
                idx++;
            }
            return this.instance.get(url)
                .then(function (response) {
                    return response.data;
                })
                .catch(function (error) {
                    this._default.exceptionHandler(error);
                })
        }
        if (method === 'POST') {
            return this.instance.post(url, param)
                .then(function (response) {
                    return response.data;
                })
                .catch(function (error) {
                    this._default.exceptionHandler(error)
                });
        }
    },
    setExceptionHandler: function (fc) {
        this._default.exceptionHandler = fc;
    }
}