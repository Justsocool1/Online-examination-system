import axios from 'axios'

export function request(config){
    var instance1 = axios.create({
        //baseURL:"http://192.168.123.219:8081",
        baseURL:"http://localhost:7777/online-examination-system/api",
        timeout:1000000
    })
    return instance1(config)
}