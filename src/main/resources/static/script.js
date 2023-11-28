'use strict';

import PaginatedTable from './PaginatedTable.js';
import {getDataRequest, getMajorsRequest } from "./RestOperations.js";

async function addUsers() {
    console.log('Adding users...');
    const mockMajors = [
        'Computer science',
        'Mathematics',
        'Physics',
        'Chemistry',
        'Biology',
        'English',
        'History',
        'Psychology',
        'Sociology',
        'Economics',
        'Business',
        'Accounting',
        'Marketing',
        'Finance',
        'Management',
        'Political science',
        'Philosophy',
        'Art',
        'Music',
        'Theater',
        'Film',
        'Dance',
        'Engineering',
        'Architecture',
        'Medicine',
        'Nursing',
        'Dentistry',
        'Pharmacy',
        'Law',
        'Education',
        'Kinesiology',
        'Nutrition',
        'Criminal justice',
        'Culinary arts',
        'Other'
    ];

    const mockNames = [
        'Ahmed Khalil',
        'Fatima Ali',
        'Yusuf Hassan',
        'Amina Mahmoud',
        'Omar Farid',
        'Leila Abadi',
        'Mohammed Nasser',
        'Layla Hamid',
        'Yasmin Said',
        'Ibrahim Shahin',
        'Mariam Abbas',
        'Mustafa Rizk',
        'Nadia Khoury',
        'Samir Haddad',
        'Kareem Saad',
        'Aisha Fawzi',
        'Amir Sharif',
        'Sara Ahmed'
    ];
    const mockPhoneNumbers = () => `12 3456 ${Math.floor(1000 + Math.random() * 9000)}`;
    const mockGpa = () => (Math.random() * 4).toFixed(2);

    for (let i = 0; i < 200; i++) {
        const user = {
            name: `${mockNames[Math.floor(Math.random() * mockNames.length)]}`,
            major: mockMajors[Math.floor(Math.random() * mockMajors.length)],
            phoneNumber: mockPhoneNumbers(),
            gpa: mockGpa()
        };

        try {
            await table.addGeneratedItems(user);
        } catch (error) {
            console.error(`Error adding user ${user.name}:`, error);
        }
    }
}

async function loadMajors() {
    const majorSelect = document.getElementById('major');
    const majors = await getMajorsRequest();
    for (let major of majors) {
        const option = document.createElement('option');
        option.value = major;
        option.innerText = major;
        majorSelect.appendChild(option);
    }
}

let table = new PaginatedTable(5, ...await getDataRequest());

const deleteButton = document.getElementById('delete');
deleteButton.addEventListener('click', table.deleteItem.bind(table));

const addItemForm = document.getElementById('addItemForm');
addItemForm.addEventListener('submit', table.addItem.bind(table));

const exportButton = document.getElementById('export');
exportButton.addEventListener('click', table.exportData.bind(table));

const generateButton = document.getElementById('generate');
generateButton.addEventListener('click', addUsers);

await loadMajors();
table.displayTable();
table.displayPagination();