'use strict';

const baseUrl = 'http://localhost:8080';

async function addItemRequest(newItem) {
    const apiUrl = baseUrl + '/add-student';
    let res = null;
    try {
        let response = await fetch(apiUrl, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newItem)
        });

        if(response.status === 400) {
            let error = await response.json();
            alert(error.message);
            throw new Error(`Invalid input! ${error.message}`);
        }

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        res = await response.json();
    } catch (error) {
        console.error('Fetch error:', error);
    }

    return res;
}

async function deleteItemsRequest(ids) {
    const apiUrl = baseUrl + '/delete-students';
    const requestOptions = {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ ids }),
    };

    try {
        const response = await fetch(apiUrl, requestOptions);

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        console.log('Server response:', await response.text());
    } catch (error) {
        console.error('Fetch error:', error);
    }
}

async function exportDataRequest() {
    const apiUrl = baseUrl + '/export-all-students';
    const response = await fetch(apiUrl, {
        method: "GET",
    });

    if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
    }

    return await response.blob();
}

async function getDataRequest() {
    const apiUrl = baseUrl + '/load-all-students';
    let data = [];
    try {
        const response = await fetch(apiUrl);

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        data = await response.json();
    } catch (error) {
        console.error('Fetch error:', error);
    }

    return data;
}

async function getMajorsRequest() {
    const apiUrl = baseUrl + '/get-all-majors';
    let data = [];
    try {
        const response = await fetch(apiUrl);

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        data = await response.json();
    } catch (error) {
        console.error('Fetch error:', error);
    }

    return data;
}

async function getHeaders() {
    const apiUrl = baseUrl + '/get-headers';
    let data = [];
    try {
        const response = await fetch(apiUrl);

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        data = await response.json();
    } catch (error) {
        console.error('Fetch error:', error);
    }

    return data;
}

export { addItemRequest, deleteItemsRequest, exportDataRequest, getDataRequest, getMajorsRequest, getHeaders };