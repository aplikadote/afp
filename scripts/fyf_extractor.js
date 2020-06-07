var tables = $('table');
var trs = $(tables[1]).find('tr');

var data = {};
data.columns = [];

var ths = $(trs[0]).find('th');
for (var i = 0; i < ths.length; i++) {
    data.columns.push(ths[i].innerText);
}

data.rows = [];
for (var i = 1; i < trs.length; i++) {
    var tds = $(trs[i]).find('td');
    var row = [];
    for (var j = 0; j < tds.length; j++) {
        row.push(tds[j].innerText);
    }
    data.rows.push(row);
}

copy({'data': data});
