<template>
    <div class="container">
        <table class="table">
            <tbody>
            <tr>
                <td>Default</td>
                <td>Defaultson</td>
                <td>def@somemail.com</td>
            </tr>
            <tr class="success">
                <td>Success</td>
                <td>Doe</td>
                <td>john@example.com</td>
            </tr>
            <tr class="danger">
                <td>Danger</td>
                <td>Moe</td>
                <td>mary@example.com</td>
            </tr>
            <tr class="info">
                <td>Info</td>
                <td>Dooley</td>
                <td>july@example.com</td>
            </tr>
            <tr class="warning">
                <td>Warning</td>
                <td>Refs</td>
                <td>bo@example.com</td>
            </tr>
            <tr class="active">
                <td>Active</td>
                <td>Activeson</td>
                <td>act@example.com</td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    export default {
        name: "ListOfLessons"
    };
    for (var i = 0; i < table.length; i++) {
        myTable += "<tr><td>" + table[i].name + " " + table[i].surname + "</td>";
        myTable += "<td>" + "<input value='09:00' type='time' min='0:00' max='23:59'/>" + "</td>";
        myTable += "<td>" + "<input value='10:30' type='time' min='0:00' max='23:59'/>" + "</td>";
        myTable += "<td>"+ "<input class='checkbox' type='checkbox'/>" +"</td></tr>";
    }
    myTable += "</table>";
</script>

<style scoped>

</style>
