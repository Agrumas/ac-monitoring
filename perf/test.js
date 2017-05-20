const _ = require('lodash');
const java = require('java');
const async = require('async');
const Table = require('cli-table');
java.classpath.push("./ac.ear");

const AnticheatServer = java.import('kth.ID2212.ac.common.AnticheatServer');
const Naming = java.import('java.rmi.Naming');

const table = new Table({head: ['Request', 'Time'], colWidths: [10, 15]});

const server = Naming.lookupSync(AnticheatServer.NAME);
console.log("Server alive: ", server.pingSync());

function testLogin (callback) {
    server.connect('tester', 'test', null, callback);
}

function testPing (callback) {
    server.ping(callback);
}

var testFunc = testLogin;

const start = process.hrtime();
const PARALEL_CLIENTS = 5000;
async.times(PARALEL_CLIENTS, (n, next) => {
    testFunc((err, data) => {
        const time = process.hrtime(start);
        table.push([n, time[0] * 1000 + time[1] / 1000000]);
        next(err);
    });
}, (err, users) => {
    if (err) {
        console.error(err);
        return;
    }
    const time = process.hrtime(start);
    const duration = time[0] * 1000 + time[1] / 1000000;
    //console.log(table.toString());
    console.log('Done ' + PARALEL_CLIENTS + ' requests in ', duration.toFixed(2), 'ms');
    const avgTime = avg(table);
    console.log('Avg: ', avgTime.toFixed(2), 'ms, Avg(%)' ,(avgTime/duration).toFixed(2),' req/sec: ', (PARALEL_CLIENTS / duration * 1000).toFixed(2));
});

function avg(table) {
    return _.reduce(table, function (sum, [_, ms]) {
        return sum + ms;
    }, 0) / table.length;
}