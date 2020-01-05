const express = require('express');
const request = require('request');
const _ = require('lodash');
const bodyParser = require('body-parser');

const PORT = 8080;
let apiKey = '3IGR30B8QW9DPS2R';
const DEFAULT_STOCK = 'MSFT'; // microsoft stock

let app = express();
app.use(bodyParser.json());

app.get('/', (req, res) => { 
    res.send("Welcome to Idan's stock fetcher server");
});

// default stock 
app.get('/stock', (req, res) => { 
    let url = `https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=${DEFAULT_STOCK}&apikey=${apiKey}`;
    request(url, function (err, response, body) {
        if(err){
            console.log('error:', error);
            return res.status(500).error(err);
        } else {
            let stock = JSON.parse(body)
            let message = `${stock["Global Quote"]["01. symbol"]} stock price is ${stock["Global Quote"]["05. price"]}.`;
            console.log(message);
            return res.status(200).json(stock);
        }
    });
});

// user request stock 
app.post('/stock', (req, res) => {
    console.dir(req.body);
    let newStock = req.body.stock || DEFAULT_STOCK;
    console.log("Got POST request to /stock, using stock=" + newStock);

    let url = `https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=${newStock}&apikey=${apiKey}`;
    request(url, function (err, response, body) {
        if(err){
            console.log('error:', error);
            return res.status(500).error(err);
        } else {
            let stock = JSON.parse(body)
            let message = `${stock["Global Quote"]["01. symbol"]} stock price is ${stock["Global Quote"]["05. price"]}.`;
            console.log(message);
            return res.status(200).json(stock);
        }
    });
});

app.listen(PORT, () => {
    console.log(`Listening on port ${PORT}`);
});