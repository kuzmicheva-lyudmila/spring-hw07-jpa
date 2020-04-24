package ru.otus.homework.controller;

class ControllerAnswer {
    static final String RESULT = "<!DOCTYPE html>\r\n" +
            "<html lang=\"en\">\r\n" +
            "<head>\r\n" +
            "    <meta charset=\"UTF-8\">\r\n" +
            "    <title>List of books</title>\r\n" +
            "</head>\r\n" +
            "<body>\r\n" +
            "    <h1>Books:</h1>\r\n" +
            "    <table class=\"books\">\r\n" +
            "        <thead>\r\n" +
            "            <tr>\r\n" +
            "                <th>ID</th>\r\n" +
            "                <th>Name</th>\r\n" +
            "                <th>Genre</th>\r\n" +
            "                <th>Authors</th>\r\n" +
            "                <th>Description</th>\r\n" +
            "            </tr>\r\n" +
            "        </thead>\r\n" +
            "        <tbody>\r\n" +
            "            <tr>\r\n" +
            "                <td>0</td>\r\n" +
            "                <td>new book</td>\r\n" +
            "                <td></td>\r\n" +
            "                <td></td>\r\n" +
            "                <td></td>\r\n" +
            "                <td>\r\n" +
            "                    <a href=\"/view?id=0\">View</a>\r\n" +
            "                </td>\r\n" +
            "                <td>\r\n" +
            "                    <a href=\"/update?id=0\">Edit</a>\r\n" +
            "                </td>\r\n" +
            "                <td>\r\n" +
            "                    <a href=\"/delete?id=0\">Delete</a>\r\n" +
            "                </td>\r\n" +
            "            </tr>\r\n" +
            "        </tbody>\r\n" +
            "    </table>\r\n" +
            "\r\n" +
            "    <div class=\"row\">\r\n" +
            "        <a href=\"/add\">Add book</a>\r\n" +
            "    </div>\r\n" +
            "\r\n" +
            "</body>\r\n" +
            "</html>";
}
