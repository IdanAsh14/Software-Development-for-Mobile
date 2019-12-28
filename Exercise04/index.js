const express = require('express');
const fs = require("fs");
const _ = require('lodash');

const PORT = 3000;
const ERROR_ID = -1;
const taskPath = './tasks.json';

let app = express();

app.get('/', (req, res) => { 
    res.send("Welcome to Idan's TODO list server");
});

// /tasks - show all tasks
app.get('/tasks', (req, res) => { 
     fs.readFile(taskPath, 'utf8', (err, taskFile) => {
        if (err) {
            console.error(err);
            return;
        }
        res.status(200).send(JSON.parse(taskFile));
    });
});

// /task/new?id=1&task=do something - add new task
app.post('/tasks/new', (req, res) => {
    readFile(tasks => {
        const newTaskId = req.query.id || ERROR_ID;
        tasks[newTaskId.toString()] = {
            "id" : parseInt(req.query.id),
            "title" : req.query.task
        };
        writeFile(JSON.stringify(tasks, null, 2), () => {
            res.status(201).send(`new task id:${newTaskId} added`);
        });
    },
        true);
});

///tasks/remove?id=1 - remove
app.delete('/tasks/remove', (req, res) => { 
    readFile(tasks => {
        const userId = req.query.id || ERROR_ID;
        delete tasks[userId];
        writeFile(JSON.stringify(tasks, null, 2), () => {
            res.status(200).send(`task id:${userId} removed`);
        });
    },
        true);
});

app.listen(PORT, () => {
    console.log(`Listening on port ${PORT}`);
});

// helper method - for read a file
const readFile = (callback, returnJson = false, filePath = taskPath, encoding = 'utf8') => {
    fs.readFile(filePath, encoding, (err, data) => {
        if (err) {
            console.error(err);
            return;
        }
        callback(returnJson ? JSON.parse(data) : data);
    });
};

// helper method - to write to file
const writeFile = (fileData, callback, filePath = taskPath, encoding = 'utf8') => {
    fs.writeFile(filePath, fileData, encoding, (err) => {
        if (err) {
            console.error(err);
            return;
        }
        callback();
    });
};