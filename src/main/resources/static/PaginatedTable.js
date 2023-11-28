'use strict';

import { addItemRequest, deleteItemsRequest, exportDataRequest, getHeaders } from './RestOperations.js';

class PaginatedTable {
    constructor(itemsPerPage, ...data) {
        this.data = data;
        this.itemsPerPage = itemsPerPage;
        this.currentPage = 1;
        this.constructTableHeaders();
    }

    async addItem(event) {
        event.preventDefault();

        const nameInput = document.getElementById('name');
        const majorInput = document.getElementById('major');
        const phoneInput = document.getElementById('phone');
        const gpaInput = document.getElementById('gpa');

        const newName = nameInput.value;
        const newMajor = majorInput.value;
        const newPhone = phoneInput.value;
        const newGpa = parseFloat(gpaInput.value);

        if (newName && newMajor && newPhone && !isNaN(newGpa)) {
            const newItem = {
                name: newName,
                major: newMajor,
                phoneNumber: newPhone,
                gpa: newGpa
            };

            let res = await addItemRequest(newItem);
            if (res !== null) {
                this.data.push(res);
                alert('Item added successfully!');
            }
            this.displayTable();
            this.displayPagination();

            nameInput.value = '';
            majorInput.value = '';
            phoneInput.value = '';
            gpaInput.value = '';
        } else {
            alert('Please enter valid values.');
        }
    }

    async addItemddd(json) {
        const newName = json.name;
        const newMajor = json.major;
        const newPhone = json.phoneNumber;
        const newGpa = json.gpa;

        if (newName && newMajor && newPhone && !isNaN(newGpa)) {
            const newItem = {
                name: newName,
                major: newMajor,
                phoneNumber: newPhone,
                gpa: newGpa
            };

            console.log(newItem);

            let res = await addItemRequest(newItem);
            if (res !== null) {
                this.data.push(res);
            }
            this.displayTable();
            this.displayPagination();

        } else {
            console.log(typeof newGpa);
        }
    }

    async deleteItem(event) {
        event.preventDefault();

        const ids = this.getIdsOfSelectedItems();

        await deleteItemsRequest(ids);

        for (let id of ids) {
            // this has O(n) time complexity, this is sufficient for small to medium-sized data sets
            // So binary search might be more overhead since this method is implemented in c++ and is very fast compared to a binary search in js
            const index = this.data.findIndex(item => item.id === id);
            this.data.splice(index, 1);
        }

        this.displayTable();
        this.displayPagination();
        alert('Items deleted successfully!');
    }

    async exportData() {
        try {
            const blob = await exportDataRequest();
            const blobUrl = window.URL.createObjectURL(blob);
            const a = document.createElement("a");
            a.href = blobUrl;
            a.download = "data.tsv";
            a.style.display = "none";
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(blobUrl);
            document.body.removeChild(a);

        } catch (error) {
            console.error("Error:", error);
            alert("An error occurred while downloading the file.");
        }

    }

    displayTable() {
        const tableBody = document.querySelector('#myTable tbody');
        tableBody.innerHTML = '';

        const startIndex = (this.currentPage - 1) * this.itemsPerPage;
        const endIndex = startIndex + this.itemsPerPage;

        for (let i = startIndex; i < endIndex && i < this.data.length; i++) {
            const row = document.createElement('tr');
            row.innerHTML = `<td><input type="checkbox"></td><td>${this.data[i].id}</td><td>${this.data[i].name}</td><td>${this.data[i].major}</td><td>${this.data[i].phoneNumber}</td><td>${this.data[i].gpa}</td>`;
            tableBody.appendChild(row);
        }
    }

    displayPagination() {
        const totalPages = this.getNumPages();
        const paginationElement = document.querySelector('#pagination');

        paginationElement.innerHTML = '';

        for (let i = 1; i <= totalPages; i++) {
            const li = document.createElement('li');
            li.innerText = i;
            li.addEventListener('click', () => {
                this.goToPage(i);
                this.displayTable();
                this.highlightCurrentPage();
            });
            paginationElement.appendChild(li);
        }

        this.highlightCurrentPage();
    }

    highlightCurrentPage() {
        const paginationElement = document.querySelector('#pagination');
        const pages = paginationElement.getElementsByTagName('li');

        for (let i = 0; i < pages.length; i++) {
            pages[i].classList.remove('active');
        }

        pages[this.currentPage - 1].classList.add('active');
    }

    getNumPages() {
        let numPages = Math.ceil(this.data.length / this.itemsPerPage);
        return numPages > 0 ? numPages : 1;
    }

    goToPage(pageNumber) {
        this.currentPage = pageNumber;
    }

    getIdsOfSelectedItems() {
        const tableBody = document.querySelector('#myTable tbody');
        const rows = tableBody.getElementsByTagName('tr');

        const ids = [];
        for (let i = 0; i < rows.length; i++) {
            const row = rows[i];
            const checkbox = row.firstChild.firstChild;

            if (checkbox.checked) {
                const id = parseInt(row.querySelector('td:nth-child(2)').innerText);
                ids.push(id);
            }
        }

        return ids;
    }

    async constructTableHeaders() {
        const tableHead = document.querySelector('#myTable thead');
        tableHead.innerHTML = '';
        const headerRow = document.createElement('tr');
        let headers = await getHeaders();
        headerRow.innerHTML = '<th></th>';
        for (let header of headers) {
            headerRow.innerHTML += `<th>${header}</th>`;
        }
        tableHead.appendChild(headerRow);
    }
}

export default PaginatedTable;