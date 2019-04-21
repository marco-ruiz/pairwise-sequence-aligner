
const apiRequest = async (url, method, jsonBody, responseConsumer) => {
    const body = jsonBody ? JSON.stringify(jsonBody) : undefined;
    const options = {
        method,
        body,
        headers: {
            'Content-Type': 'application/json'
        }
    };

    const res = await fetch(url, options)
    const json = await res.json();
    return responseConsumer ? responseConsumer(json) : json;
}

export const apiRequestGet = async (url, responseConsumer) => {
    return apiRequest(url, 'GET', undefined, responseConsumer);
}

export const apiRequestPost = (url, jsonBody, responseConsumer) => {
    return apiRequest(url, 'POST', jsonBody, responseConsumer);
}
