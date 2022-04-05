import axios from 'axios'
// const baseURL = "http://192.168.123.219:7777/online-examination-system/api";
const baseURL = "http://localhost:7777/online-examination-system/api";
export function request(config){
    
    var instance = axios.create({
        //baseURL:"http://192.168.123.219:8081",
        baseURL,
        timeout:1000000
    })
    instance.interceptors.request.use((config) =>{
        config.headers.token = localStorage.getItem("token");
        return config;
      })
    return instance(config)
}


