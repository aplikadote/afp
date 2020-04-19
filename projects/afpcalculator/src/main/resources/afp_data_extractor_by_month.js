var fondos = ['A', 'B', 'C', 'D', 'E'];
var columns = ['AFP', 'RENTAB_MENSUAL', 'RENTAB_DESDE_ENERO', 'RENTAB_ULTIMOS_12_MESES', 'PROMEDIO_ANUAL_DESDE_20020927'];

var year = $('select[name="aaaa"]').val();
var month = $('select[name="mm"]').val();
var tables = $('form table');

var data = [];
for (var i = 0; i < 5; i++) {
    var keys = $(tables[i + 2]).find('tr td:nth-child(1)').map(function (x, y) {
        return y.innerText;
    });
    var rentMen = $(tables[i + 2]).find('tr td:nth-child(2)').map(function (x, y) {
        return y.innerText;
    });
    var rentEne = $(tables[i + 2]).find('tr td:nth-child(3)').map(function (x, y) {
        return y.innerText;
    });
    var rentYear = $(tables[i + 2]).find('tr td:nth-child(4)').map(function (x, y) {
        return y.innerText;
    });
    var rentAve = $(tables[i + 2]).find('tr td:nth-child(5)').map(function (x, y) {
        return y.innerText;
    });
    var n = keys.length;

    var rows = [];
    for (var j = 0; j < n; j++) {
        var row = [];
        row.push(keys[j]);
        row.push(rentMen[j]);
        row.push(rentEne[j]);
        row.push(rentYear[j]);
        row.push(rentAve[j]);
        rows.push(row);
    }
    data.push({'fondo': fondos[i], 'columns': columns, 'rows': rows});
}
var result = {'year': year, 'month': month, 'data': data};
copy(result);
