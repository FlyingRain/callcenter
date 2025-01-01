import axios from "axios";

const instance = axios.create({
    baseURL: "http://111.231.64.229:8080",
    timeout: 10000,
    headers: {
        "Content-Type": "application/json",
    }
})

instance.interceptors.request.use(
    config => {
        return config
    }, error => {
        console.log(error)
        return Promise.reject(error)
    }
)

export default instance