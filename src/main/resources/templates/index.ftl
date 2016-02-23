<!DOCTYPE html>
<html ng-app="DemoTodoList">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Demo Todo App">
    <title>Todo</title>

    <link rel="stylesheet" href="/webjars/fontawesome/4.5.0/css/font-awesome.min.css">
    <style type="text/css">
        #list-mgmt label {
            white-space: nowrap;
        }
        ol li input {
            width: 70%;
            margin-right: 5pt;
        }
        .messages {
            height: 3ex;
        }
    </style>
</head>
<body>
    <div ng-controller="MainCtrl">
        <h1>{{ name || "Todo List" }}</h1>

        <form id="list-mgmt">
            <label>List name: <input type="text" placeholder="Name" ng-model="name"></label>
            <label>Password: <input type="password" placeholder="****" ng-model="password"></label>
            <button type="button" ng-click="load()">Load</button>
            <button type="button" ng-click="save()">Save</button>
            <div class="messages"><span ng-show="messages">{{ messages }}</span></div>
        </form>

        <div>
            <ol>
                <li ng-repeat="item in items">
                    <input type="text" ng-model="item.text">
                    <button type="button" ng-click="moveUp(item)" ng-disabled="$first"><i class="fa fa-arrow-up"></i></button>
                    <button type="button" ng-click="moveDown(item)" ng-disabled="$last"><i class="fa fa-arrow-down"></i></button>
                    <button type="button" ng-click="remove(item)"><i class="fa fa-remove"></i></button>
                </li>
                <li>
                    <input type="text" ng-model="newitem.text" placeholder="New item">
                    <button type="button" ng-click="add()"><i class="fa fa-plus"></i></button>
                </li>
            </ol>
        </div>
    </div>

    <script src="/webjars/angular/1.4.9/angular.js"></script>
    <script src="/webjars/lodash/4.2.0/lodash.js"></script>
    <script src="/webjars/jsSHA/2.0.2/src/sha256.js"></script>
    <script src="/static/app/application.js"></script>
</body>
</html>