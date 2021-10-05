import axios from 'axios'

const standard = axios.create({
    //baseURL: 'https://sa-backend-spring.herokuapp.com/',
    baseURL: 'http://localhost:8082/',
    timeout: 100000,
    transformResponse: [
        function (data) {
            return data
        }
    ]
})

export default standard
