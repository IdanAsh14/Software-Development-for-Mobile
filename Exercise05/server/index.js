const express = require('express');
const request = require('request');
const _ = require('lodash');
const bodyParser = require('body-parser');
const admin = require('firebase-admin');
const serviceAccountKey = require('/Users/idan/Desktop/alpha-vantage-stock-firebase-adminsdk-vrbad-bfb18115b6.json')

const PORT = 8080;
let alphaVantageApiKey = '3IGR30B8QW9DPS2R';
const DEFAULT_STOCK = 'MSFT'; // microsoft stock
var tokens = {};
var id_counter = 1;
var intervalId = -1;

let app = express();
let firebaseApp = admin.initializeApp({
    credential: admin.credential.cert(serviceAccountKey),
    databaseURL: '"https://alpha-vantage-stock.firebaseio.com"'
});
app.use(bodyParser.json());

app.get('/', (req, res) => { 
    res.send("Welcome to Idan's stock fetcher server");
});

//get token from client
app.post('/:user/token', (req, res, next) => {
    let token = req.body.token;
    if (!token) return res.status(400).json({err: "missing token"});
    
    let id = isTokenExist(token);
    let obj = {};
    //new users
    if(id === -1){
        tokens[id_counter] = {"token": token, "stock": ''};
        obj = {"id": id_counter};
        console.log(`Received new token request from ${id_counter} for token=${token}`);
        id_counter++;
    }
    //exist user
    else {
        tokens[id]["token"] = token;
        obj = {"id": id};
        console.log(`Received token request from ${id} for token=${token}`);
    }
   
    res.status(200).json(obj);
});

//check if the token exist, if yes return the user id, else return -1
function isTokenExist(token){
    var id = -1;
    Object.keys(tokens).forEach(key => {
        if(tokens[key]["token"] === token){
            id = key;
        }
    });
    return id;
}

// user request stock 
app.post('/:user/stock', (req, res) => {
    console.dir(req.body);
    let newStock = req.body.stock || DEFAULT_STOCK;
    let id = req.body.id;
    tokens[id]["stock"] =  newStock;
    console.log(`Got POST request to ${id}/stock, using stock=` + newStock);
    getStock(id);
    if(intervalId !== -1){
        clearInterval(intervalId);
        intervalId = -1;
    }
    intervalId = setInterval(getStock, 15000, [id]);
    return res.status(200);
});

function getStock(id){
    let url = `https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=${tokens[id]["stock"]}&apikey=${alphaVantageApiKey}`;
    request(url, function (err, response, body) {
        if(err){
            return err;
        } else {
            let stock = JSON.parse(body);
            if (stock.hasOwnProperty("Global Quote")){
                let stockName = stock["Global Quote"]["01. symbol"];
                let stockPrice = stock["Global Quote"]["05. price"];
                let message = `${stockName} stock price is ${stockPrice}.`;
                console.log(message);
                sendMessageToFCM(id, stockName, stockPrice);
            } else {
                clearInterval(intervalId);
                intervalId = -1;
                sendMessageToFCM(id, "The stock didn't found", "-1");
            }
        }
    });
}

function sendMessageToFCM(id, stockName, price){
    var message = {
        data: {
        name: stockName,
        price: price
        },
        token: tokens[id]["token"]
    };
    
    admin.messaging().send(message)
    .then((response) => {
        console.log('Successfully sent message:', response);
    })
    .catch((error) => {
        console.log('Error sending message:', error);
    });
}

app.listen(PORT, () => {
    console.log(`Listening on port ${PORT}`);
});