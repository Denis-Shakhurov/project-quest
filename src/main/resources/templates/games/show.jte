@import dto.GamePage
@param GamePage page

@template.layout.page(
content = @`
    <section>
        <div class="container-lg mt-5">
            <h1>${page.getGame().getName()}</h1>
        </div>
    </section>
`
)