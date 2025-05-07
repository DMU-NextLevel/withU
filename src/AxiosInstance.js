import axios from "axios"

export const api = axios.create({
    baseURL: 'https://nextlevel.r-e.kr/',
    withCredentials: true
})
