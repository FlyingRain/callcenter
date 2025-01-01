import axios from "axios";

const instance = axios.create({
    baseURL: "http://127.0.0.1:8080",
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